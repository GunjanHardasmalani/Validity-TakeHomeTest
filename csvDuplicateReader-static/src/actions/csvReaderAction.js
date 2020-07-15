import axios from 'axios';

class UploadService{

/**
 *
 * @param {*} formData send POST request to server
 */
uploadCSV(formData) {
      return (axios.post('api/upload', formData));
    }

}

export default new UploadService();
