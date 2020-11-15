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

    const pollForActivities = async () => {
        const res = await API.getActivity(workshopCode);

        if(res.status === 200){
            setRedCards(res.data.activity.reds)
        }
    }

    useEffect(() => {
        pollForActivities();
    }, [])

    useInterval(async () => {
        await pollForActivities();
    }, 2000);


    let redCardRender;

    if(redCards.length === 0){
        redCardRender = <Col>
            <h2>No red cards found</h2>
        </Col>
    } else {
        redCardRender = redCards.map((item, i) => {
            return <Col key={i}>
                <Card />
            </Col>
        })
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
        </div>
    )

}

export default Host;