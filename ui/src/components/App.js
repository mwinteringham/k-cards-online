import Container from 'react-bootstrap/Container';
import Welcome from './Welcome';
import { HashRouter, Route } from 'react-router-dom'

function App() {
  return (
    <div className="App">
      <Container className="text-center mt-5">
        <HashRouter>
          <Route exact path='/' component={Welcome} />
        </HashRouter>
      </Container>
    </div>
  );
}

export default App;
