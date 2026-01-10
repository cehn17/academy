package com.cehn17.academy.student.service;

import com.cehn17.academy.student.dto.StudentResponseDTO;
import com.cehn17.academy.student.dto.StudentUpdateRequest;
import com.cehn17.academy.student.entity.Student;
import com.cehn17.academy.student.mapper.StudentMapper;
import com.cehn17.academy.student.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {


    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentResponseDTO getMyProfile(String username) {
        return studentRepository.findByUserUsername(username) // Asumiendo que agregas este método al repo
                .map(s -> new StudentResponseDTO(
                        s.getId(), s.getName(), s.getLastName(),
                        s.getDni(), s.getAddress(),
                        s.getUser().getUsername(), s.getUser().getEmail()
                ))
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
    }

    @Override
    @Transactional
    public StudentResponseDTO updateMyProfile(String username, StudentUpdateRequest request) {

        Student student = studentRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado para el usuario: " + username));

        studentMapper.updateStudentFromDto(request, student);

        Student updatedStudent = studentRepository.save(student);

        return studentMapper.toResponseDTO(updatedStudent);
    }

    @Override
    public Page<StudentResponseDTO> getAllStudents(Pageable pageable) {

        Page<Student> studentsPage = studentRepository.findAll(pageable);

        // Ojo: Este .map() es de la clase Page, no es un Stream normal.
        return studentsPage.map(studentMapper::toResponseDTO);
    }

    @Override
    public StudentResponseDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado para el id: " + id));

        return studentMapper.toResponseDTO(student);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado para el id: " + id));
        studentRepository.delete(student);
    }

    @Override
    public StudentResponseDTO updateStudent(Long id, StudentUpdateRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado para el id: " + id));

        studentMapper.updateStudentFromDto(request, student);

        Student updatedStudent = studentRepository.save(student);

        return studentMapper.toResponseDTO(updatedStudent);
    }
}
