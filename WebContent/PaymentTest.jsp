<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PaymentTest</title>
</head>
<body>
        <form action="PaymentProcess" method="POST">
            <script
                src="https://checkout.stripe.com/checkout.js" class="stripe-button"
                data-key="pk_test_lM0LpViawa5I6QswMtG7ck0X00AFLMkrjP"
                data-amount="999"
                data-name="TestBiz"
                data-description="Example charge"
                data-image="https://stripe.com/img/documentation/checkout/marketplace.png"
                data-locale="auto"
                data-currency="eur">
            </script>
 
 
        </form>
    </body>
</html>