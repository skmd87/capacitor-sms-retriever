import Foundation

@objc public class CapacitorSmsRetriever: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
