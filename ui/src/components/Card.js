import BoostrapCard from 'react-bootstrap/Card';

const Card = ({type}) => {

    return(
        <div>
            <BoostrapCard body bg={type}>
                <BoostrapCard.Body>
                    <h3>Card details</h3>
                </BoostrapCard.Body>
            </BoostrapCard>
        </div>
    )

}

export default Card;