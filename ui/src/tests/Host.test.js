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
            reds : [{
                code : 'abc',
                name : 'Mark'
            },{
                code : 'def',
                name : 'Mary'
            },{
                code : 'ghi',
                name : 'Sam'
            },{
                code : 'jkl',
                name : 'Jane'
            }],
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
                    code : 'abc',
                    name : 'Green Mark'
                },
                subThread : [{
                    code : 'def',
                    name: 'Yellow Mark'
                }]
            },{
                cardDetail : {
                    code : 'ghi',
                    name : 'Green Jeff'
                }
            }]
        }
    });

    const hostComponent = render(<Host />);
    
    await waitFor(() => screen.getAllByText(/Card/)); 

    expect(hostComponent.baseElement).toMatchSnapshot();
});

test('renders yellow cards', async () => {
    mock.onGet('/workshop/empty-workshop-code/activity').reply(200, {
        activity : {
            reds : [],
            threads : [{
                cardDetail : {
                    code : 'abc',
                    name : 'Green Mark'
                },
                subThread : [{
                    code : 'def',
                    name : 'Yellow Mark'
                },{
                    code : 'ghi',
                    name : 'Yello Jeff'
                }]
            },{
                cardDetail : {
                    code : 'klm',
                    name : 'Green Jeff'
                }
            }]
        }
    });

    const hostComponent = render(<Host />);
    
    await waitFor(() => screen.getAllByText(/Card/)); 

    expect(hostComponent.baseElement).toMatchSnapshot();
});