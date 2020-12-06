import { render, fireEvent, screen, waitFor } from '@testing-library/react';
import Attendee from '../components/Attendee';
import { Router } from 'react-router-dom';
import { createMemoryHistory } from 'history';
import axios from 'axios';

let MockAdapter = require('axios-mock-adapter');

const mock = new MockAdapter(axios);
const history = createMemoryHistory();

test('renders the Attendee component', () => {
    const attendeeComponent = render(<Attendee />);
    
    expect(attendeeComponent.baseElement).toMatchSnapshot();
});

test('confirm message appears when sending a card', async () => {
    mock.onPost('/workshop/empty-workshop-code/card', { cardType : 'green', attendeeCode : 'empty-attendee-code'}).reply(201);

    const attendeeComponent = render(<Attendee />);
    
    fireEvent.click(screen.getByTestId(/greenCard/i));
    await waitFor(() => screen.getByText('Close alert')); 

    expect(attendeeComponent.baseElement).toMatchSnapshot();
});

test('joining a new workshop takes you to a new page', async () => {
    mock.onDelete('/workshop/empty-workshop-code/leave', { code : 'empty-attendee-code' }).reply(202);
  
    history.push('/attendee')
    render(<Router history={history}><Attendee /></Router>)
   
    fireEvent.click(screen.getByText(/Leave Workshop/i));
  
    await waitFor(() => expect(history.location.pathname).toBe('/'));
});

test('an error message is shown when card not send', async () => {
    render(<Attendee />);

    fireEvent.click(screen.getByTestId(/greenCard/i));

    await waitFor(() => expect(screen.getByTestId('cardError')).toHaveTextContent('An error occurred. The server responded with a 404'));
});
