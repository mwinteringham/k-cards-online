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

    const pollForActivities = async () => {
        const res = await API.getActivity(workshopCode);

        if(res.status === 200){
            if(res.data.activity.reds.length > 0){
                setRedCards(res.data.activity.reds);
            }

            if(res.data.activity.threads.length > 0){
                setGreenCards(res.data.activity.threads);
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

    if(redCards.length === 0){
        redCardRender = <Col>
            <h2>No red cards found</h2>
        </Col>
    } else {
        redCardRender = redCards.map((item, i) => {
            return <Col key={i}>
                <Card />
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
            return <Row key={i}>
                <Col>
                    <p>Green card</p>
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