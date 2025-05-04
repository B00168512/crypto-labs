import jwt

# is a default secret in https://pypi.org/project/PyJWT/
jwtkey = "secret"
encodedToken = jwt.encode({
    "username": "gregory","admin": True
}, jwtkey, algorithm="HS256")

print(encodedToken)