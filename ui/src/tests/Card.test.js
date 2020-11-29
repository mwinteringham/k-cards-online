import Card from '../components/Card';
import { render, waitFor, screen, fireEvent } from '@testing-library/react';
import axios from 'axios';

let MockAdapter = require('axios-mock-adapter');

const mock = new MockAdapter(axios);

test('renders the Card component', async () => {
    const cardComponent = render(<Card type={'red'} name={'Mark'} code={'abc'} />);

    await waitFor(() => screen.getAllByText(/Mark/i)); 

    expect(cardComponent.baseElement).toMatchSnapshot();
});

test('send a delete request for Card', async () => {
    let mockTrigger = false;
    const refreshMock = () => {
        mockTrigger = true;
    }

    mock.onDelete('/workshop/empty-workshop-code/card/abc').reply(202);

    render(<Card type={'red'} name={'Mark'} code={'abc'} refresh={refreshMock}/>);

    await waitFor(() => screen.getAllByText(/Mark/i)); 
    fireEvent.click(screen.getByText(/Delete/i));

    await waitFor(() => expect(mock.history.delete.length).toBe(1));

    expect(mockTrigger).toBe(true);
});