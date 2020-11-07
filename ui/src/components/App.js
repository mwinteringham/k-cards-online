import Container from 'react-bootstrap/Container';
import Welcome from './Welcome';
import Host from './Host';
import Attendee from './Attendee';
import { HashRouter, Switch, Route } from 'react-router-dom'

function App() {
  return (
    <div className="App">
      <Container className="text-center mt-5">
        <HashRouter>
          <Switch>
            <Route exact path='/' component={Welcome} />
            <Route exact path='/host' component={Host} />
            <Route exact path='/attendee' component={Attendee} />
          </Switch>
        </HashRouter>
      </Container>
    </div>
  );
}

export default App;
