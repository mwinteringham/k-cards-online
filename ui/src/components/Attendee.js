import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import Alert from 'react-bootstrap/Alert';
import API from '../api/api';
import { useState } from 'react';
import { useGlobalState } from './state';

function Attendee(){

    const [showConfirm, setConfirm] = useState(false);
    const [workshopCode] = useGlobalState('workshopCode');

    async function sendCard(cardType){
        console.log(workshopCode);
        const res = await API.sendCard(cardType);

        if(res.statusCode === 201){
            setConfirm(true);
        }
    } 

    return(
        <div>
            {(showConfirm && <Alert variant="success" onClose={() => setConfirm(false)} dismissible><Alert.Heading>Your card has been received by the Host</Alert.Heading></Alert>)}
            <Row>
                <Col>
                    <Button data-testid='greenCard' className='btn-success' onClick={() => sendCard('green')} style={{ width: '100%', height: '8rem'}}>Send Green Card</Button>
                </Col>
                <Col>
                    <Button data-testid='yellowCard' className='btn-warning' onClick={() => sendCard('yellow')} style={{ width: '100%', height: '8rem' }}>Send Yellow Card</Button>
                </Col>
                <Col>
                    <Button data-testid='redCard' className='btn-danger' onClick={() => sendCard('red')} style={{ width: '100%', height: '8rem' }}>Send Red Card</Button>
                </Col>
            </Row>
            <Row className='mt-4'>
                <Col>
                    <Button className='btn-secondary' style={{ width: '100%', height: '4rem'}}>Leave Workshop</Button>
                </Col>
            </Row>
        </div>
    );
}

export default Attendee