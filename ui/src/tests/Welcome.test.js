import { render } from '@testing-library/react';
import Welcome from '../components/Welcome';

test('renders learn react link', () => {
  const welcomeComponent = render(<Welcome />);
  
  expect(welcomeComponent.baseElement).toMatchSnapshot();
});
