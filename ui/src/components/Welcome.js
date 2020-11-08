import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button';
import API from '../api/api';
import { useState } from 'react';

import { useHistory } from 'react-router-dom';
import { useGlobalState } from './state';

function Welcome() {

    const [workshop, setWorkshopName] = useState('');
    const [attendeeName, setAttendeeName] = useState('');
    const [workshopCode, setWorkshopCode] = useState('');
    const [value, updateWorkshop] = useGlobalState('workshopCode');

    const history = useHistory();

    async function createWorkshop(){
        const res = await API.createWorkshop({
            name : workshop
        });
        
        if(res.status === 201){
            history.push('host');
        }
    }

    async function joinWorkshop(){
        const res = await API.joinWorkshop(workshopCode, {
            name : attendeeName
        });

        if(res.status === 201){
            updateWorkshop(res.data.code);
            history.push('attendee')
        }
    }

    return (
        <div>
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
                    <Form>
                    <Form.Control data-testid='attendeeName' className='mb-3' placeholder='Your name' onChange={e => setAttendeeName(e.target.value) } />
                    <Form.Control data-testid='workshopCode' className='mb-3' placeholder='Workshop code' onChange={e => setWorkshopCode(e.target.value) }/>
                    <Button data-testid='joinWorkshop' onClick={joinWorkshop}>Join Workshop</Button>
                    </Form>
                </Col>
                <Col>
                    <h2>Host a Workshop</h2>
                    <p>Enter the name of the workshop and click Start Workshop</p>
                    <Form>
                    <Form.Control data-testid='workshopName' placeholder='Workshop name' className='mb-3' onChange={e => setWorkshopName(e.target.value)}/>
                    <Button data-testid='startWorkshop' className='mt-5' onClick={createWorkshop}>Start Workshop</Button>
                    </Form>
                </Col>
                <Col />
            </Row>
        </div>
    )

}

export default Welcome;