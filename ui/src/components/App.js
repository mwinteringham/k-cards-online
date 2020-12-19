import Welcome from './Welcome';
import Host from './Host';
import Attendee from './Attendee';
import Footer from './Footer';
import Privacy from './Privacy';
import Cookie from './Cookie';
import { HashRouter, Switch, Route } from 'react-router-dom'

function App() {

  return (
    <HashRouter>
      <div className="App">
        <Switch>
          <Route exact path='/' component={Welcome} />
          <Route exact path='/host' component={Host} />
          <Route exact path='/attendee' component={Attendee} />
          <Route exact path='/cookie' component={Cookie} />
          <Route exact path='/privacy' component={Privacy} />
        </Switch>
      </div>
      <Footer />
    </HashRouter>
  );
}

export default App;
