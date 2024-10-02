# quant-dev-assignment-pnl

Quant Trading and Pricing Calculator

Clone the Repository
git clone https://github.com/shashicode2n4mcoffee/quant-dev-assignment-pnl.git
cd quant-dev-assignment-pnl

API Endpoints 
Profit & Loss Calculation (CSV-based)
Endpoint: /api/trades/upload
Method: POST
Input : CSV file with data like 
![image](https://github.com/user-attachments/assets/a7d2d5f5-deee-47e6-962c-2cb899356cf7)
Response : 
[
    {
        "quantity": 13,
        "price": 83.395,
        "realizedPnL": 0.0,
        "unrealizedPnL": 0.0
    },
    {
        "quantity": 2,
        "price": 83.4,
        "realizedPnL": 0.0,
        "unrealizedPnL": 0.06500000000012562
    },
    {
        "quantity": -6,
        "price": 83.4025,
        "realizedPnL": 0.04500000000004434,
        "unrealizedPnL": 0.05750000000004718
    }
]

Best Aggregated Prices Calculation
Endpoint: /api/pricing/best-prices
Method: GET
Response : 
{
  "3": {
    "targetTier": 3,
    "offers": [
      {"bankName": "Bank A", "quantity": 1, "price": 10.1},
      {"bankName": "Bank B", "quantity": 2, "price": 19.8}
    ],
    "totalPrice": 29.9
  },
  "5": {
    "targetTier": 5,
    "offers": [
      {"bankName": "Bank D", "quantity": 5, "price": 49.5}
    ],
    "totalPrice": 49.5
  },
  "10": {
    "targetTier": 10,
    "offers": [
      {"bankName": "Bank B", "quantity": 10, "price": 95.0}
    ],
    "totalPrice": 95.0
  },
  "15": {
    "targetTier": 15,
    "offers": [
      {"bankName": "Bank A", "quantity": 5, "price": 50.0},
      {"bankName": "Bank B", "quantity": 10, "price": 95.0}
    ],
    "totalPrice": 145.0
  },
  "20": {
    "targetTier": 20,
    "offers": [
      {"bankName": "Bank C", "quantity": 20, "price": 180.0}
    ],
    "totalPrice": 180.0
  }
}


