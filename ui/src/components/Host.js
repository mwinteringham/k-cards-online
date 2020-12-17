import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Navbar from 'react-bootstrap/Navbar';
import Container from 'react-bootstrap/Container';
import Button from 'react-bootstrap/Button';
import Alert from 'react-bootstrap/Alert';
import Card from './Card';
import { useHistory } from 'react-router-dom';
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

    const [workshopCode, updateWorkshopCode] = useGlobalState('workshopCode');
    const [redCards, setRedCards] = useState([]);
    const [greenCards, setGreenCards] = useState([]);
    const [yellowCards, setYellowCards] = useState([]);
    const [showError, setShowError] = useState(false);
    const [errorCode, setErrorCode] = useState('');

    const history = useHistory();

    const leaveWorkshop = async () => {
        const res = await API.deleteWorkshop(workshopCode);

        if(res.status !== 202){
            console.error('Attempted to delete workshop but failed. Received error: ' + res);
        }
        
        history.push('/');
    }

    const pollForActivities = async () => {
        const res = await API.getActivity(workshopCode);

        if(res.status === 200){
            setRedCards(res.data.activity.reds);

            setGreenCards(res.data.activity.threads);

            if(res.data.activity.threads.length > 0){
                setYellowCards(res.data.activity.threads[0].subThread);
            }
        } else {
            setErrorCode(res.status);
            setShowError(true);
        }
    }

    useEffect(() => {
        if(workshopCode === 'empty-workshop-code'){
            history.push('/');
        }

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
            <Navbar bg='light'>
                <Navbar.Brand>Welcome! Your workshop code is: <span style={{color: 'blue'}}>{workshopCode}</span></Navbar.Brand>
                <Navbar.Toggle />
                <Navbar.Collapse className='justify-content-end'>
                    <Navbar.Text>
                        <Button data-testid='leaveWorkshop' onClick={() => leaveWorkshop()}>Close Workshop</Button>
                    </Navbar.Text>
                </Navbar.Collapse>
            </Navbar>
            {(showError && <Alert variant='danger' onClose={() => setShowError(false)} dismissible><Alert.Heading data-testid='hostError'>An error has occurred. The server responded with a {errorCode}</Alert.Heading></Alert>)}
            <Container fluid>
                <Row>
                    {redCardRender}
                </Row>
                <Row className='mt-2 border'></Row>
                {greenCardRender}
            </Container>
        </div>
    )

}

export default Host;