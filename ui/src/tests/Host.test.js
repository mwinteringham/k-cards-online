import { render, screen, waitFor } from '@testing-library/react';
import Host from '../components/Host';
import axios from 'axios';

let MockAdapter = require('axios-mock-adapter');

const mock = new MockAdapter(axios);

test('renders the Host component', async () => {
    mock.onGet('/workshop/empty-workshop-code/activity').reply(200, {
        activity : {
            reds : [],
            threads : []
        }
    });

    const hostComponent = render(<Host />);

    await waitFor(() => screen.getAllByText(/No red cards found/i)); 

    expect(hostComponent.baseElement).toMatchSnapshot();
});

test('renders red cards', async () => {
    mock.onGet('/workshop/empty-workshop-code/activity').reply(200, {
        activity : {
            reds : [
                'one',
                'two',
                'three',
                'four'
            ],
            threads : []
        }
    });

    const hostComponent = render(<Host />);
    
    await waitFor(() => screen.getAllByText(/Card/)); 

    expect(hostComponent.baseElement).toMatchSnapshot();
});

test('renders green cards', async () => {
    mock.onGet('/workshop/empty-workshop-code/activity').reply(200, {
        activity : {
            reds : [],
            threads : [{
                cardDetail : {
                    name : "Mark"
                },
                subThread : [{
                    "name": "Mark"
                }]
            },{
                cardDetail : {
                    name : "Mark"
                }
            }]
        }
    });

    const hostComponent = render(<Host />);
    
    await waitFor(() => screen.getAllByText(/Green card/)); 

    expect(hostComponent.baseElement).toMatchSnapshot();
});

test('renders yellow cards', async () => {
    mock.onGet('/workshop/empty-workshop-code/activity').reply(200, {
        activity : {
            reds : [],
            threads : [{
                cardDetail : {
                    name : "Mark"
                },
                subThread : [{
                    "name": "Mark"
                },{
                    "name": "Mark"
                }]
            },{
                cardDetail : {
                    name : "Mark"
                }
            }]
        }
    });

    const hostComponent = render(<Host />);
    
    await waitFor(() => screen.getAllByText(/Yellow card/)); 

    expect(hostComponent.baseElement).toMatchSnapshot();
});