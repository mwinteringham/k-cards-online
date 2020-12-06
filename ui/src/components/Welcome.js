import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import API from '../api/api';
import { useState } from 'react';

import { useHistory } from 'react-router-dom';
import { useGlobalState } from '../state/state';

function Welcome() {

    const [workshop, setWorkshopName] = useState('');
    const [attendeeName, setAttendeeName] = useState('');
    const [workshopCode, setWorkshopCode] = useState('');
    const [workshopErrorMessage, setWorkshopErrorMessage] = useState('');
    const [workshopValue, updateWorkshop] = useGlobalState('workshopCode');
    const [attendeeValue, updateAttendeeCode] = useGlobalState('attendeeCode');
    const [workshopValidated, setValidated] = useState(false);
    const [joinValidated, setJoinValidated] = useState(false);

    const history = useHistory();

    const triggerWorkshopCreationError = (event, msg) => {
        setWorkshopErrorMessage(msg);
        event.preventDefault();
        event.stopPropagation();
        setValidated(true);
    }

    const createWorkshop = async (event) => {
        if(workshop.length > 0){
            const res = await API.createWorkshop({
                name : workshop
            });
    
            if(res.status === 201){
                updateWorkshop(res.data.code);
                history.push('host');
            } else {
                triggerWorkshopCreationError(event, 'An error occurred when creating your workshop');
            }
        } else {
            triggerWorkshopCreationError(event, 'Please enter a name for your workshop');
        }
    }

    const joinWorkshop = async (event) => {
        if(attendeeName.length > 2 && workshopCode.length > 0){
            const res = await API.joinWorkshop(workshopCode, {
                name : attendeeName
            });
    
            if(res.status === 201){
                updateWorkshop(workshopCode);
                updateAttendeeCode(res.data.code);
                history.push('attendee');
            } else if (res.status === 404){
                setWorkshopCode('');
                event.preventDefault();
                event.stopPropagation();
                setJoinValidated(true);    
            }
        } else {
            event.preventDefault();
            event.stopPropagation();
            setJoinValidated(true);
        }
    }

    return (
        <div>
            <Container fluid className='text-center mt-5'>
                <Row className='mb-5'>
                    <Col>
                        <h1>Welcome to K-Cards online</h1>
                    </Col>
                </Row>
                <Row>
                    <Col />
                    <Col>
                        <h2>Join a Workshop</h2>
                        <p>Enter your name and the workshop code to Join Workshop</p>
                        <Form noValidate validated={joinValidated} onSubmit={joinWorkshop} data-testid='joinWorkshop'>
                            <Form.Control required value={attendeeName} type='text' data-testid='attendeeName' className='mb-3' placeholder='Your name' onChange={e => setAttendeeName(e.target.value) } />
                            <Form.Control.Feedback type='invalid' data-testid='usernameError'>
                                Please enter a name that has at least two characters
                            </Form.Control.Feedback>
                            <Form.Control required value={workshopCode} type='text' data-testid='workshopCode' className='mb-3' placeholder='Workshop code' onChange={e => setWorkshopCode(e.target.value) }/>
                            <Form.Control.Feedback type='invalid' data-testid='workshopCodeError'>
                                Please enter a valid workshop code
                            </Form.Control.Feedback>
                            <Button type='submit'>Join Workshop</Button>
                        </Form>
                    </Col>
                    <Col>
                        <h2>Host a Workshop</h2>
                        <p>Enter the name of the workshop and click Start Workshop</p>
                        <Form noValidate validated={workshopValidated} onSubmit={createWorkshop} data-testid='startWorkshop'>
                            <Form.Control required type='text' data-testid='workshopName' placeholder='Workshop name' className='mb-3' onChange={e => setWorkshopName(e.target.value)}/>
                            <Form.Control.Feedback type='invalid' data-testid='hostError'>
                                {workshopErrorMessage}
                            </Form.Control.Feedback>
                            <Button className='mt-5' type='submit'>Start Workshop</Button>
                        </Form>
                    </Col>
                    <Col />
                </Row>
            </Container>
        </div>
    )

}

export default Welcome;
