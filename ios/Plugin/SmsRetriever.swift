import Foundation

@objc public class SmsRetriever: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
