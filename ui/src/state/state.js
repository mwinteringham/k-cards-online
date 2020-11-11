import { createGlobalState } from 'react-hooks-global-state';

export const { useGlobalState } = createGlobalState({
  workshopCode : 'empty-workshop-code',
  attendeeCode : 'empty-attendee-code'
});