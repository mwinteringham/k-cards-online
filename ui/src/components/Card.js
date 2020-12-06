import BoostrapCard from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import { useGlobalState } from '../state/state';
import API from '../api/api';

const Card = ({type, name, code, refresh}) => {

    const [workshopCode] = useGlobalState('workshopCode');

    const deleteCard = async () => {
        const res = await API.deleteCard(workshopCode, code);

        if(res.status === 202){
            refresh()
        } else {
            console.error('Attempted to delete card: ' + code + ' but received the error: ' + res);
        }
    }

    return(
        <div>
            <BoostrapCard className="mt-2" body bg={type}>
                <BoostrapCard.Title style={{marginTop : '.75rem'}}>
                    <Button className='float-right btn-light' onClick={deleteCard}>Delete</Button>
                    <h2>{name}</h2>
                </BoostrapCard.Title>
            </BoostrapCard>
        </div>
    )

}

export default Card;