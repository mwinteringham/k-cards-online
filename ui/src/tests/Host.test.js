import { render, screen, waitFor, fireEvent } from '@testing-library/react';
import Host from '../components/Host';
import axios from 'axios';
import { createMemoryHistory } from 'history';
import { Router } from 'react-router-dom';

let MockAdapter = require('axios-mock-adapter');

const mock = new MockAdapter(axios);
const history = createMemoryHistory();

test('renders the Host component', async () => {
    mock.onGet('/workshop/empty-workshop-code/activity').reply(200, {
        activity : {
            reds : [],
            threads : []
        }
    });

    const hostComponent = render(<Router history={history}><Host /></Router>);

    await waitFor(() => screen.getAllByText(/Waiting for Red cards/i)); 

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

    const hostComponent = render(<Router history={history}><Host /></Router>);
    
    await waitFor(() => screen.getAllByText(/Jane/)); 

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

    const hostComponent = render(<Router history={history}><Host /></Router>);
    
    await waitFor(() => screen.getAllByText(/Green Jeff/)); 

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

    const hostComponent = render(<Router history={history}><Host /></Router>);
    
    await waitFor(() => screen.getAllByText(/Green Jeff/)); 

    expect(hostComponent.baseElement).toMatchSnapshot();
});

test('Delete workshop when leaving', async () => {
    mock.onDelete('/workshop/empty-workshop-code').reply(202);

    history.push('/host');
    render(<Router history={history}><Host /></Router>);

    fireEvent.click(screen.getByTestId(/leaveWorkshop/i));

    await waitFor(() => expect(history.location.pathname).toBe('/'));
});

test('An error is shown when activity request fails', async () => {
    mock.onGet('/workshop/empty-workshop-code/activity').reply(500);

    render(<Router history={history}><Host /></Router>);

    await waitFor(() => expect(expect(screen.getByTestId('hostError')).toHaveTextContent('An error has occurred. The server responded with a 500')));
});

test('Empty workshop code causes a redirect back to welcome', async () => {
    render(<Router history={history}><Host /></Router>);

    await waitFor(() => expect(history.location.pathname).toBe('/'));
});