# Getting Started with Create React App

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

CREATE TABLE student_filter (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    specialisation_id BIGINT NOT NULL,
    placement_id BIGINT NOT NULL,
    domain_id BIGINT NOT NULL,
    CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    CONSTRAINT fk_specialisation FOREIGN KEY (specialisation_id) REFERENCES specialisations(id) ON DELETE CASCADE,
    CONSTRAINT fk_placement FOREIGN KEY (placement_id) REFERENCES placements(id) ON DELETE CASCADE,
    CONSTRAINT fk_domain FOREIGN KEY (domain_id) REFERENCES domains(id) ON DELETE CASCADE
);

CREATE TABLE student_filter (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    specialisation_id BIGINT NOT NULL,
    placement_id BIGINT,
    domain_id BIGINT NOT NULL,
    CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    CONSTRAINT fk_specialisation FOREIGN KEY (specialisation_id) REFERENCES specialisations(id) ON DELETE CASCADE,
    CONSTRAINT fk_placement FOREIGN KEY (placement_id) REFERENCES placements(id) ON DELETE CASCADE,
    CONSTRAINT fk_domain FOREIGN KEY (domain_id) REFERENCES domains(id) ON DELETE CASCADE
);

INSERT INTO placements (organisation_id, profile, description, intake, min_grade) 
VALUES 
(4, 'System Design Engineer', 'Responsible for developing and maintaining software systems.', 50, 7.5),
(1, 'Big Data Researcher', 'Analyze data to provide business insights and reporting.', 30, 6.8);


CREATE TABLE student_courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    grade_id BIGINT,
    comments TEXT,
    FOREIGN KEY (student_id) REFERENCES student(id),
    FOREIGN KEY (course_id) REFERENCES courses(id),
    FOREIGN KEY (grade_id) REFERENCES grades(id)
);

CREATE TABLE placement_student (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    placement_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    cv_application MEDIUMBLOB,
    about TEXT,
    acceptance BOOLEAN,
    comments TEXT,
    date TIMESTAMP,
    FOREIGN KEY (placement_id) REFERENCES placements(id),
    FOREIGN KEY (student_id) REFERENCES student(id)
);



## Available Scripts

In the project directory, you can run:

### `npm start`

Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in your browser.

The page will reload when you make changes.\
You may also see any lint errors in the console.

### `npm test`

Launches the test runner in the interactive watch mode.\
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `npm run build`

Builds the app for production to the `build` folder.\
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.\
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

### `npm run eject`

**Note: this is a one-way operation. Once you `eject`, you can't go back!**

If you aren't satisfied with the build tool and configuration choices, you can `eject` at any time. This command will remove the single build dependency from your project.

Instead, it will copy all the configuration files and the transitive dependencies (webpack, Babel, ESLint, etc) right into your project so you have full control over them. All of the commands except `eject` will still work, but they will point to the copied scripts so you can tweak them. At this point you're on your own.

You don't have to ever use `eject`. The curated feature set is suitable for small and middle deployments, and you shouldn't feel obligated to use this feature. However we understand that this tool wouldn't be useful if you couldn't customize it when you are ready for it.

## Learn More

You can learn more in the [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started).

To learn React, check out the [React documentation](https://reactjs.org/).

### Code Splitting

This section has moved here: [https://facebook.github.io/create-react-app/docs/code-splitting](https://facebook.github.io/create-react-app/docs/code-splitting)

### Analyzing the Bundle Size

This section has moved here: [https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size](https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size)

### Making a Progressive Web App

This section has moved here: [https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app](https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app)

### Advanced Configuration

This section has moved here: [https://facebook.github.io/create-react-app/docs/advanced-configuration](https://facebook.github.io/create-react-app/docs/advanced-configuration)

### Deployment

This section has moved here: [https://facebook.github.io/create-react-app/docs/deployment](https://facebook.github.io/create-react-app/docs/deployment)

### `npm run build` fails to minify

This section has moved here: [https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify](https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify)
