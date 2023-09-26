# Track
## 19 Sept 2023
### work done
1. started the project
2. added user service
3. added register endpoint in user service
## 20 Sept 2023
### issue found
1. user is able to register itself using same email
### work done
1. fixed [20 Sept 2023 | issue | 1]
## 21 Sept 2023
### work done
1. added getAllUser endpoint in user service
2. added getUserByUserId endpoint in user service
3. added swagger in user service
## 23 Sept 2023
### work done
1. added updateUser endpoint in user service
2. added deleteUserByUserId endpoint in user service
3. added discovery-service to the project
4. added auth-service to the project
5. added zipkin monitoring to the project
6. added product service
7. added deleteAll endpoint in user service
## 24 Sept 2023
### work done
1. added cart-service to the project
2. run createCart when registerUser runs
3. run deleteCartByCartId when deleteUserByUserId runs
4. run deleteAll cart when deleteAll user runs
5. added price calculation to cart service
## 26 Sept 2023
1. added login endpoint to user service
2. added gateway service to project
3. added security to user service
4. added authorization in user service
### issue found
1. add method security in user service
2. add security in all other service
3. add public route in RouteValidator in gateway service
4. add method to handle unauthorized requests