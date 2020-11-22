import BoostrapCard from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import { useGlobalState } from '../state/state';
import API from '../api/api';

const Card = ({type, name, code, refresh}) => {

    const [workshopCode] = useGlobalState('workshopCode');

    const deleteCard = async () => {
        const res = await API.deleteCard(workshopCode, [code]);

        if(res.status === 202){
            {refresh()}
        }
    }

    return(
        <div>
            <BoostrapCard body bg={type}>
                <BoostrapCard.Body>
                    <p>{name}</p>
                    <h3>Card details</h3>
                    <Button onClick={deleteCard}>Delete</Button>
                </BoostrapCard.Body>
            </BoostrapCard>
        </div>
    )

}

export default Card;