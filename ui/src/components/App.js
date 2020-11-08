import Container from 'react-bootstrap/Container';
import Welcome from './Welcome';
import Host from './Host';
import Attendee from './Attendee';
import { HashRouter, Switch, Route } from 'react-router-dom'
import { useState } from 'react';

function App() {

  const [workshopCode, setWorkshopCode] = useState('');

  return (
    <div className="App">
      <Container className="text-center mt-5">
        <HashRouter>
          <Switch>
            <Route exact path='/' setWorkshopCode={setWorkshopCode} component={Welcome} />
            <Route exact path='/host' component={Host} />
            <Route exact path='/attendee' workshopCode={workshopCode} component={Attendee} />
          </Switch>
        </HashRouter>
      </Container>
    </div>
  );
}

export default App;
