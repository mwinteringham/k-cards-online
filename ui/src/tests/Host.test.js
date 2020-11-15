import { render, screen, waitFor } from '@testing-library/react';
import Host from '../components/Host';
import axios from 'axios';

let MockAdapter = require('axios-mock-adapter');

const mock = new MockAdapter(axios);

test('renders the Host component', async () => {
    mock.onGet('/workshop/empty-workshop-code/activity').reply(200, {
        activity : {
            reds : [
                'one',
                'two',
                'three',
                'four'
            ]
        }
    });

    const hostComponent = render(<Host />);
    
    await waitFor(() => screen.getAllByText('Card')); 

    expect(hostComponent.baseElement).toMatchSnapshot();
});