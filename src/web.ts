import { WebPlugin } from '@capacitor/core';

import type { SmsRetrieverPlugin } from './definitions';

export class SmsRetrieverWeb extends WebPlugin implements SmsRetrieverPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
