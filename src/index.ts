import { registerPlugin } from '@capacitor/core';

import type { CapacitorSmsRetrieverPlugin } from './definitions';

const CapacitorSmsRetriever = registerPlugin<CapacitorSmsRetrieverPlugin>('CapacitorSmsRetriever', {
  web: () => import('./web').then(m => new m.CapacitorSmsRetrieverWeb()),
});

export * from './definitions';
export { CapacitorSmsRetriever };
