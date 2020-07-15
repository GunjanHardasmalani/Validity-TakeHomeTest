# CSV Duplicate Reader
This applications find duplicates in a csv file.

## About the app

React uses to fetch the .csv file from the computer and the file is read through a Spring-boot application, spring application parses the csv file and show duplicate and non duplicate rows using react 

* Spring application uses Levenshtein Distance as to get matching rows and Double Metaphone to  filter out the result into multiple set of duplicates and a single set of non duplicate values

# Install dependencies
* yarn 
> npm i -g yarn 

* use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) 

 * Use Right Version of node.js 

    The repo is setup to run a specific version of node.js.  This version is managed in the `.nvmrc` file in the root directory.
    
    In order to avoid errors related to node engine mismatches, make sure to run `nvm use` upon entering the root directory in each new terminal session. It will automatically download and set the active version to the correct one.

# Start the application in dev mode

##  Back-end

* Navigate to backend Folder
```bash
 cd csvDuplicateReader-svc
```

* Run Maven Command
```bash
 ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```
* You will now have a host running at
> [localhost:9091](http://localhost:9091/)

## Front-end

* Navigate to Frontend Folder
```bash
 cd csvDuplicateReader-static
```

* Run Frontend
```bash
yarn run start
```
* You will now have a host running at
> [localhost:9000](http://localhost:9000/)


# Refrences

* [Levenshtein Distance](https://www.baeldung.com/java-levenshtein-distance#:~:text=The%20Levenshtein%20distance%20is%20a,Insertion%20of%20a%20character%20c)
* [Metaphone Distance](https://xlinux.nist.gov/dads/HTML/doubleMetaphone.html#:~:text=Definition%3A%20An%20algorithm%20to%20code,such%20as%20a%20foreign%20word.)
* [React Component](https://reactjs.org/)
* [Map Functions in React](https://upmostly.com/tutorials/how-to-for-loop-in-react-with-examples)
* [Nesting in React](https://stackoverflow.com/questions/28329382/understanding-unique-keys-for-array-children-in-react-js)
* [Get Mapping](https://developer.okta.com/blog/2018/07/19/simple-crud-react-and-spring-boot)
