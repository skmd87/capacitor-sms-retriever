export interface StartSuccessResponse {
  body: string;
}

export interface ErrorResponse {
  reason: string;
}
export interface CapacitorSmsRetrieverPlugin {
  startListening(): Promise<StartSuccessResponse>;
  stopListening(): Promise<void>;
}
