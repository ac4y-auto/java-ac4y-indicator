# java-ac4y-indicator

Indicator domain objects, persistence adapters, and service layer for analytics, transfers, and balance history.

## Coordinates

- **GroupId**: `ac4y`
- **ArtifactId**: `ac4y-indicator`
- **Version**: `1.0.0`

## Description

Core indicator module with domain objects for analytics, transfers, and balance history. Includes JDBC persistence adapters and service layer with standard, by-origin, and by-indicator variants. Supports both `Ac4yIndicator*` and `Indicator*`/`Origin*` naming patterns.

### Key Classes

- `Ac4yIndicator` - Main indicator domain object
- `Ac4yIndicatorAnalytics` / `IndicatorAnalytics` / `OriginAnalytics` - Analytics domain objects with list wrappers
- `Ac4yIndicatorTransfer` / `IndicatorTransfer` / `OriginTransfer` - Transfer domain objects with list wrappers
- `Ac4yIndicatorBalanceHistory` / `IndicatorBalanceHistory` - Balance history domain objects with list wrappers
- `Ac4yIndicatorAnalyticsDBAdapter` / `Ac4yIndicatorTransferDBAdapter` / `Ac4yIndicatorBalanceHistoryDBAdapter` - JDBC adapters
- `Ac4yIndicatorService` / `Ac4yIndicatorAnalyticsService` / `Ac4yIndicatorTransferService` - Service layer with standard and by-origin variants

## Dependencies

- `ac4y-base4jsonandxml` (JSON/XML serialization)
- `ac4y-database-basic` (Ac4yDBAdapter)
- `ac4y-g8h-basic` (GUID-to-HumanId mapping)
- `ac4y-connection-pool` (DBConnection)

## Build

```bash
mvn clean package
```

## Origin

Extracted from `IJIndicatorModule/Ac4yIndicator`.
