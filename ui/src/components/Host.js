import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import Card from './Card';
import { useGlobalState } from '../state/state';
import { useEffect, useState } from 'react';
import API from '../api/api';

function Host(){

    const [workshopCode] = useGlobalState('workshopCode');
    const [redCards, setRedCards] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const res = await API.getActivity(workshopCode);
    
            if(res.status === 200){
                setRedCards(res.data.reds)
            }
        }

        fetchData();
    }, []);

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
                {redCards.map((item, i) => {
                    return <Col key={i}>
                        <Card />
                    </Col>
                })}
            </Row>
        </div>
    )

}

export default Host;