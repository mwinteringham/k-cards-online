import { render, fireEvent, screen, waitFor} from '@testing-library/react';
import Welcome from '../components/Welcome';
import { Router } from 'react-router-dom';
import { createMemoryHistory } from 'history';
import axios from 'axios';

var MockAdapter = require('axios-mock-adapter');

const mock = new MockAdapter(axios);
const history = createMemoryHistory();

test('renders the Welcome component', () => {
  const welcomeComponent = render(<Welcome />);
  
  expect(welcomeComponent.baseElement).toMatchSnapshot();
});

test('creating a new workshop takes you to a new page', async () => {
  mock.onPost('/workshop', { name: 'BREWT' }).reply(201, { code : 'abc'});

  render(<Router history={history}><Welcome /></Router>)

  fireEvent.change(screen.getByTestId(/workshopName/i), {
    target: {value: 'BREWT'}
  })
  fireEvent.submit(screen.getByTestId(/startWorkshop/i));

  await waitFor(() => expect(history.location.pathname).toBe('/host'));
});

test('joining a new workshop takes you to a new page', async () => {
  mock.onPost('/workshop/abc/join', { name: 'Mark' }).reply(201, { code : 'abc'});

  render(<Router history={history}><Welcome /></Router>)

  fireEvent.change(screen.getByTestId('workshopCode'), {
    target: {value: 'abc'}
  });
  fireEvent.change(screen.getByTestId(/attendeeName/i), {
    target: {value: 'Mark'}
  });
  fireEvent.submit(screen.getByTestId(/joinWorkshop/i));

  await waitFor(() => expect(history.location.pathname).toBe('/attendee'));
});

test('submitting a blank workshop name returns an error', async () => {
  render(<Welcome />);

  fireEvent.submit(screen.getByTestId(/startWorkshop/i));

  expect(screen.getByTestId('hostError')).toHaveTextContent('Please enter a name for your workshop');
});

test('submitting a blank workshop connection returns an error', async () => {
  render(<Welcome />);

  fireEvent.submit(screen.getByTestId(/joinWorkshop/i));
  expect(screen.getByTestId('usernameError')).toHaveTextContent('Please enter a name that has at least two characters');
  expect(screen.getByTestId('workshopCodeError')).toHaveTextContent('Please enter a valid workshop code');

});

test('submitting an invalid workshop code returns an error', async () => {
  mock.onPost('/workshop/def/join', { name: 'Geoff' }).reply(404);

  render(<Welcome />);

  fireEvent.change(screen.getByTestId('workshopCode'), {
    target: {value: 'def'}
  });
  fireEvent.change(screen.getByTestId(/attendeeName/i), {
    target: {value: 'Geoff'}
  });
  fireEvent.submit(screen.getByTestId(/joinWorkshop/i));

  await waitFor(() => expect(screen.getByTestId('workshopCodeError')).toHaveTextContent('Please enter a valid workshop code'));
});
