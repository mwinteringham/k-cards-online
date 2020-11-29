import Welcome from './Welcome';
import Host from './Host';
import Attendee from './Attendee';
import { HashRouter, Switch, Route } from 'react-router-dom'

function App() {

  return (
    <div className="App">
        <HashRouter>
          <Switch>
            <Route exact path='/' component={Welcome} />
            <Route exact path='/host' component={Host} />
            <Route exact path='/attendee' component={Attendee} />
          </Switch>
        </HashRouter>
    </div>
  );
}

export default App;
