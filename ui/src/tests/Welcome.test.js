import { render, fireEvent, screen, waitFor} from '@testing-library/react';
import Welcome from '../components/Welcome';
import { Router } from 'react-router-dom';
import { createMemoryHistory } from 'history';
import axios from 'axios';

var MockAdapter = require('axios-mock-adapter');

const mock = new MockAdapter(axios);
const history = createMemoryHistory();

test('renders learn react link', async () => {
  const welcomeComponent = render(<Welcome />);
  
  expect(welcomeComponent.baseElement).toMatchSnapshot();
});

test('creating a new workshop takes you to a new page', async () => {
  mock.onPost('/workshop', { name: 'BREWT' }).reply(201);

  render(<Router history={history}><Welcome /></Router>)

  fireEvent.change(screen.getByTestId(/workshopName/i), {
    target: {value: 'BREWT'}
  })
  fireEvent.click(screen.getByTestId(/startWorkshop/i));

  await waitFor(() => expect(history.location.pathname).toBe('/host'));
});

test('joining a new workshop takes you to a new page', async () => {
  mock.onPost('/workshop/abc/join', { name: 'Mark' }).reply(201);

  render(<Router history={history}><Welcome /></Router>)

  fireEvent.change(screen.getByTestId(/workshopCode/i), {
    target: {value: 'abc'}
  });
  fireEvent.change(screen.getByTestId(/attendeeName/i), {
    target: {value: 'Mark'}
  });
  fireEvent.click(screen.getByTestId(/joinWorkshop/i));

  await waitFor(() => expect(history.location.pathname).toBe('/attendee'));
});