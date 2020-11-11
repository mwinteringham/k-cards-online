import { render, fireEvent, screen, waitFor } from '@testing-library/react';
import Attendee from '../components/Attendee';
import axios from 'axios';

let MockAdapter = require('axios-mock-adapter');

const mock = new MockAdapter(axios);

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

