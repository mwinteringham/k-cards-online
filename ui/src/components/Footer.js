import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

function Footer() {

    return(
        <footer id="footer" className="footer">
            <Container>
                <Row>
                    <Col className="text-center mt-4">
                        <p class="text-muted"><a href="/">K-Card-Online</a> v0.4 - Created by <a href="http://www.mwtestconsultancy.co.uk">Mark Winteringham</a> - © 2019-20 <a href="/#/cookie">Cookie-Policy</a> - <a href="/#/privacy">Privacy-Policy</a></p>
                    </Col>
                </Row>
            </Container>
        </footer>
    )

}

export default Footer;