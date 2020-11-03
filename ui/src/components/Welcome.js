import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button';

function Welcome() {

    return (
        <div>
            <Row className="mb-5">
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
                <Form.Control className="mb-3" placeholder="Your name" />
                <Form.Control className="mb-3" placeholder="Workshop code" />
                <Button type="submit">Join Workshop</Button>
                </Form>
            </Col>
            <Col>
                <h2>Host a Workshop</h2>
                <p>Enter the name of the workshop and click Start Workshop</p>
                <Form>
                <Form.Control placeholder="Workshop name" className="mb-3" />
                <Button className="mt-5">Start Workshop</Button>
                </Form>
            </Col>
            <Col />
            </Row>
            <Row>
                <Col>
                
                </Col>
            </Row>
        </div>
    )

}

export default Welcome;