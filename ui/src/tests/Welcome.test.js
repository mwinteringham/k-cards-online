import { render, fireEvent, screen } from '@testing-library/react';
import Welcome from '../components/Welcome';
import { HashRouter } from 'react-router-dom';

test('renders learn react link', () => {
  const welcomeComponent = render(<Welcome />);
  
  expect(welcomeComponent.baseElement).toMatchSnapshot();
});

test('creating a new workshop takes you to a new page', () => {
  const {container} = render(<HashRouter><Welcome /></HashRouter>)

  fireEvent.click(screen.getByTestId(/startWorkshop/i));

  setTimeout(() => {
    expect(container).toHaveTextContent(/Host page/);
  }, 500);
});

test('joining a new workshop takes you to a new page', () => {
  const {container} = render(<HashRouter><Welcome /></HashRouter>)

  fireEvent.click(screen.getByTestId(/joinWorkshop/i));

  setTimeout(() => {
    expect(container).toHaveTextContent(/Attendee Page/);
  }, 500);
})