import { render, fireEvent, screen, waitFor } from '@testing-library/react';
import Welcome from '../components/Welcome';
import { Router } from 'react-router-dom';
import { createMemoryHistory } from "history";

const history = createMemoryHistory();

test('renders learn react link', async () => {
  const welcomeComponent = render(<Welcome />);
  
  expect(welcomeComponent.baseElement).toMatchSnapshot();
});

test('creating a new workshop takes you to a new page', async () => {
  render(<Router history={history}><Welcome /></Router>)

  fireEvent.click(screen.getByTestId(/startWorkshop/i));

  await waitFor(() => expect(history.location.pathname).toBe("/host"));
});

test('joining a new workshop takes you to a new page', async () => {
  render(<Router history={history}><Welcome /></Router>)

  fireEvent.click(screen.getByTestId(/joinWorkshop/i));

  await waitFor(() => expect(history.location.pathname).toBe("/attendee"));
});