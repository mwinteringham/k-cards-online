import BoostrapCard from 'react-bootstrap/Card';
import { useEffect, useState } from 'react';

const Card = ({type, name, code}) => {

    const [cardCode, setCode] = useState('');

    useEffect(() => {
        setCode(code);
    }, []);

    return(
        <div>
            <BoostrapCard body bg={type}>
                <BoostrapCard.Body>
                    <p>{name}</p>
                    <h3>Card details</h3>
                </BoostrapCard.Body>
            </BoostrapCard>
        </div>
    )

}

export default Card;