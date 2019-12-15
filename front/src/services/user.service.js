/* eslint-disable no-console */
import axios from 'axios';
import { BehaviorSubject } from 'rxjs';

const API_URL = 'http://localhost:8765/api/user/auth/';
const currentUserSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('currentUser')));
const accessToken = new BehaviorSubject(JSON.parse(localStorage.getItem('accessToken')));

class UserService {

    get currentUserValue() {
        return currentUserSubject.value;
    }

    get currentUser() {
        return currentUserSubject.asObservable();
    }

    get currentTokenValue() {
        return accessToken.value;
    }

    get currentToken() {
        return accessToken.asObservable();
    }

    login(user) {
        return axios.post(API_URL + 'signin', {
                username: user.username, 
                password: user.password
            })
            .then(response => {
            console.warn(response);

            localStorage.setItem('currentUser', JSON.stringify(response.data.user));
            currentUserSubject.next(response.data.user);

            localStorage.setItem('accessToken', JSON.stringify(response.data.token));
            accessToken.next(response.data.token);
        });
    }

    logOut() {
        localStorage.removeItem('currentUser');
        currentUserSubject.next(null);
        localStorage.removeItem('accessToken');
        accessToken.next(null);
    }

    register(user) {
        return axios.post(API_URL + 'signup', JSON.stringify(user), 
        { headers: { 'Content-Type': 'application/json' } })
        .then(response => {
            console.warn(response);
        });
    }

}

export default new UserService();