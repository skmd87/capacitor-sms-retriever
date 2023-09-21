import { registerPlugin } from '@capacitor/core';

import type { SmsRetrieverPlugin } from './definitions';

const SmsRetriever = registerPlugin<SmsRetrieverPlugin>('SmsRetriever', {
  web: () => import('./web').then(m => new m.SmsRetrieverWeb()),
});

export * from './definitions';
export { SmsRetriever };
