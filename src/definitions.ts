export interface SmsRetrieverPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
