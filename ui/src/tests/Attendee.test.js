import { render, fireEvent, screen, waitFor } from '@testing-library/react';
import Attendee from '../components/Attendee';

test('renders the Attendee component', () => {
    const attendeeComponent = render(<Attendee />);
    
    expect(attendeeComponent.baseElement).toMatchSnapshot();
});

test('confirm message appears when sending a card', async () => {
    const attendeeComponent = render(<Attendee />);
    
    fireEvent.click(screen.getByTestId(/greenCard/i));
    await waitFor(() => screen.getByText('Your card has been received by the Host')); 

    expect(attendeeComponent.baseElement).toMatchSnapshot();
});

