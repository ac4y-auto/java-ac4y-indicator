# Architektura - java-ac4y-indicator

## Attekintes

Indikator domain objektumok, perzisztencia adapterek es szolgaltatas reteg analitika, transzfer es egyenleg-torteneti muveletekhez.

## Szerkezet

```
src/main/java/ac4y/indicator/
  domain/
    Ac4yIndicator.java                          - Fo indikator domain objektum
    Ac4yIndicatorAnalytics.java                 - Analitika domain objektum
    Ac4yIndicatorAnalyticsList.java             - Analitika lista wrapper
    Ac4yIndicatorBalanceHistory.java            - Egyenleg torteneti objektum
    Ac4yIndicatorBalanceHistoryList.java        - Egyenleg torteneti lista
    Ac4yIndicatorTransfer.java                  - Transzfer domain objektum
    Ac4yIndicatorTransferList.java              - Transzfer lista wrapper
    IndicatorAnalytics.java                     - Indikator analitika
    IndicatorAnalyticsList.java                 - Indikator analitika lista
    IndicatorBalanceHistory.java                - Indikator egyenleg tortenet
    IndicatorBalanceHistoryList.java            - Indikator egyenleg lista
    IndicatorTransfer.java                      - Indikator transzfer
    IndicatorTransferList.java                  - Indikator transzfer lista
    OriginAnalytics.java                        - Forras analitika
    OriginAnalyticsList.java                    - Forras analitika lista
    OriginTransfer.java                         - Forras transzfer
    OriginTransferList.java                     - Forras transzfer lista
  persistence/
    Ac4yIndicatorAnalyticsDBAdapter.java        - Analitika JDBC adapter
    Ac4yIndicatorBalanceHistoryDBAdapter.java   - Egyenleg JDBC adapter
    Ac4yIndicatorTransferDBAdapter.java         - Transzfer JDBC adapter
  service/java/
    Ac4yIndicatorService.java                   - Fo szolgaltatas
    Ac4yIndicatorStandardService.java           - Standard szolgaltatas
    Ac4yIndicatorAnalyticsService.java          - Analitika szolgaltatas
    Ac4yIndicatorAnalyticsStandardService.java  - Standard analitika
    Ac4yIndicatorAnalyticsByOriginService.java  - Forras alapu analitika
    Ac4yIndicatorByOriginService.java           - Forras alapu szolgaltatas
    Ac4yIndicatorTransferService.java           - Transzfer szolgaltatas
    Ac4yIndicatorTransferStandardService.java   - Standard transzfer
    Ac4yIndicatorTransferByOriginService.java   - Forras alapu transzfer
```

## Fuggosegek

- ac4y-base4jsonandxml (JSON/XML szerializacio)
- ac4y-database-basic (Ac4yDBAdapter)
- ac4y-g8h-basic (GUID-HumanId lekepzes)
- ac4y-connection-pool (DBConnection)

## Eredet

Az `IJIndicatorModule/Ac4yIndicator` modulbol kinyerve.
