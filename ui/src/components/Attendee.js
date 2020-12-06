import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import Alert from 'react-bootstrap/Alert';
import API from '../api/api';
import { useState } from 'react';
import { useGlobalState } from '../state/state';
import { useHistory } from 'react-router-dom';
import Container from 'react-bootstrap/Container';

function Attendee(){

    const [showConfirm, setConfirm] = useState(false);
    const [workshopCode, updateWorkshopCode] = useGlobalState('workshopCode');
    const [attendee, updateAttendeeCode] = useGlobalState('attendeeCode');
    const [cardType, setCardType] = useState('');
    const [error, setErrorType] = useState('');
    const [showError, setShowError] = useState(false);

    const history = useHistory();

    const sendCard = async (card) => {
        const payload = {
            cardType : card,
            attendeeCode : attendee
        };

        const res = await API.sendCard(workshopCode, payload);

        if(res.status === 201){
            setCardType(card);
            setConfirm(true);
        } else {
            setErrorType(res.status);
            setShowError(true);
        }
    }

    const leaveWorkshop = async () => {
        const payload = {
            code : attendee
        }

        const res = await API.leaveWorkshopAsAttendee(workshopCode, payload);

        if(res.status === 202){
            updateWorkshopCode('');
            updateAttendeeCode('');
            history.push('/');
        }
    }

    return(
        <div>
            <Container fluid className='mt-5'>
                {(showConfirm && <Alert variant='success' onClose={() => setConfirm(false)} dismissible><Alert.Heading>Your {cardType} card has been received by the Host</Alert.Heading></Alert>)}
                {(showError && <Alert variant='danger'  onClose={() => setShowError(false)} dismissible><Alert.Heading data-testid='cardError'>An error occurred. The server responded with a {error}</Alert.Heading></Alert>)}
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
                        <Button className='btn-secondary' onClick={() => leaveWorkshop()} style={{ width: '100%', height: '4rem'}}>Leave Workshop</Button>
                    </Col>
                </Row>
            </Container>
        </div>
    );
}

export default Attendee