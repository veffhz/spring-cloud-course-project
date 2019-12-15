import axios from 'axios';
import { BehaviorSubject } from 'rxjs';

const API_URL = 'http://localhost:8765/api/course/service/';
const accessToken = new BehaviorSubject(JSON.parse(localStorage.getItem('accessToken')));

class CourseService {

    createTransaction(transaction) {
        return axios.post(API_URL + 'enroll', JSON.stringify(transaction),
            { headers: { 'Content-Type': 'application/json; charset=UTF-8' } });
    }

    filterTransactions(userId) {
        return axios.get(API_URL + 'user/' + userId, { headers: { 'Content-Type': 'application/json; charset=UTF-8' } });
    }

    filterStudents(courseId) {
        return axios.get(API_URL + 'course/' + courseId, { headers: { 
            'Content-Type': 'application/json; charset=UTF-8',
            'Token': accessToken.value
        } });
    }

    findAllCourses() {
        return axios.get(API_URL + 'all', { headers: { 'Content-Type': 'application/json; charset=UTF-8' } });
    }

}

export default new CourseService();