import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import Card from './Card';
import { useGlobalState } from '../state/state';
import React, { useState, useEffect, useRef } from 'react';
import API from '../api/api';

function useInterval(callback, delay) {
  const savedCallback = useRef();

  useEffect(() => {
    savedCallback.current = callback;
  }, [callback]);

  useEffect(() => {
    function tick() {
      savedCallback.current();
    }
    if (delay !== null) {
      let id = setInterval(tick, delay);
      return () => clearInterval(id);
    }
  }, [delay]);
}

function Host(){

    const [workshopCode] = useGlobalState('workshopCode');
    const [redCards, setRedCards] = useState([]);
    const [greenCards, setGreenCards] = useState([]);
    const [yellowCards, setYellowCards] = useState([]);

    const pollForActivities = async () => {
        const res = await API.getActivity(workshopCode);

        if(res.status === 200){
            setRedCards(res.data.activity.reds);

            setGreenCards(res.data.activity.threads);

            if(res.data.activity.threads.length > 0){
                setYellowCards(res.data.activity.threads[0].subThread);
            }
        }
    }

    useEffect(() => {
        pollForActivities();
    }, [])

    useInterval(async () => {
        await pollForActivities();
    }, 2000);


    let redCardRender;
    let greenCardRender;
    let yellowCardRender;

    if(redCards.length === 0){
        redCardRender = <Col>
            <h2>No red cards found</h2>
        </Col>
    } else {
        redCardRender = redCards.map((item, i) => {
            return <Col key={i}>
                <Card type={'danger'} name={item.name} code={item.code} refresh={pollForActivities}/>
            </Col>
        });
    }

    if(greenCards.length === 0){
        greenCardRender = 
        <Row>
            <Col>
                <h2>No green cards found</h2>
            </Col>
        </Row>
    } else {
        greenCardRender = greenCards.map((item, i) => {
            if(yellowCards.length > 0){
                yellowCardRender = yellowCards.map((item, i) => {
                    return <Row key={i}>
                        <Col><Card type={'warning'} name={item.name} code={item.code} refresh={pollForActivities}/></Col>
                    </Row>
                })
            }

            return <Row key={i}>
                <Col>
                    <Card type={'success'} name={item.cardDetail.name} code={item.cardDetail.code} refresh={pollForActivities}/>
                    {i === 0 &&
                        <div>
                            {yellowCardRender}
                        </div>
                    }
                </Col>
            </Row>
        });
    }

    return (
        <div>
            <Row>
                <Col>
                    <h1>Your workshop code is: {workshopCode}</h1>
                </Col>
                <Col>
                    <Button>Close Workshop</Button>
                </Col>
            </Row>
            <Row className="mt-5">
                {redCardRender}
            </Row>
            <Row>
                <Col>
                    <h2>Activity:</h2>
                </Col>
            </Row>
            {greenCardRender}
        </div>
    )

}

export default Host;