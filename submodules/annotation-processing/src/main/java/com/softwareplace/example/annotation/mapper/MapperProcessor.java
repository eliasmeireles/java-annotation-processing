package com.softwareplace.example.annotation.mapper;

import com.squareup.javapoet.*;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes("com.softwareplace.example.annotation.mapper.Mapper")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class MapperProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        List<TypeElement> sorted = roundEnv.getElementsAnnotatedWith(Mapper.class).stream()
                .filter(element -> element.getKind() == ElementKind.CLASS)
                .map(element -> (TypeElement) element)
                .sorted((a, b) -> {
                    Mapper aMapper = a.getAnnotation(Mapper.class);
                    Mapper bMapper = b.getAnnotation(Mapper.class);
                    return Integer.compare(aMapper.priority(), bMapper.priority());
                }).toList();

        sorted.forEach(this::generateMapper);

        return true;
    }

    private void generateMapper(TypeElement typeElement) {
        String className = typeElement.getSimpleName() + "Mapper";
        String packageName = processingEnv.getElementUtils().getPackageOf(typeElement).getQualifiedName().toString();

        // Get the class (from annotation) as a TypeMirror
        Mapper mapperAnnotation = typeElement.getAnnotation(Mapper.class);
        TypeMirror targetTypeMirror = getClassValue(mapperAnnotation);

        // Source class (annotated class)
        ClassName sourceClassName = ClassName.get(typeElement);

        // Target class (from annotation) - convert TypeMirror to ClassName
        ClassName targetClassName = ClassName.get(
                processingEnv.getTypeUtils().asElement(targetTypeMirror).getEnclosingElement().toString(),
                processingEnv.getTypeUtils().asElement(targetTypeMirror).getSimpleName().toString()
        );

        // Handle extendsTo (only extend if not Object.class)
        TypeMirror extendsToMirror = getClassValueForExtends(mapperAnnotation);
        ClassName extendsClassName = null;
        if (!extendsToMirror.toString().equals("java.lang.Object")) {
            String extendsToPackage = "";

            if (mapperAnnotation.extendsToPackage().isEmpty()) {
                extendsToPackage = processingEnv.getTypeUtils()
                        .asElement(extendsToMirror)
                        .getEnclosingElement()
                        .toString();
            } else {
                extendsToPackage = mapperAnnotation.extendsToPackage();
            }

            extendsClassName = ClassName.get(
                    extendsToPackage.replaceAll("<any\\?>", ""),
                    processingEnv.getTypeUtils().asElement(extendsToMirror).getSimpleName().toString()
            );
        }

        // Builder for the Mapper interface
        TypeSpec.Builder mapperInterfaceBuilder = TypeSpec.interfaceBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(MethodSpec.methodBuilder("map")
                        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                        .returns(targetClassName)
                        .addParameter(sourceClassName, "input")
                        .build())
                .addMethod(MethodSpec.methodBuilder("map")
                        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                        .returns(sourceClassName)
                        .addParameter(targetClassName, "input")
                        .build());

        if (mapperAnnotation.generateImpl()) {
            // Annotation for the @Mapper
            ClassName mapperAnnotationClass = ClassName.get("org.mapstruct", "Mapper");
            AnnotationSpec annotationSpec = getAnnotationSpec(mapperAnnotation, mapperAnnotationClass);
            mapperInterfaceBuilder.addAnnotation(annotationSpec);
        }

        // Add extends clause if extendsTo is not Object
        if (extendsClassName != null) {
            mapperInterfaceBuilder.addSuperinterface(extendsClassName);
        }

        // Build the interface
        TypeSpec mapperInterface = mapperInterfaceBuilder.build();

        // Write the generated file
        JavaFile javaFile = JavaFile.builder(
                        packageName + ".mapper",
                        mapperInterface)
                .build();

        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private AnnotationSpec getAnnotationSpec(Mapper mapperAnnotation, ClassName mapperAnnotationClass) {
        String componentModel = mapperAnnotation.componentModel();
        if (!componentModel.isBlank()) {
            return AnnotationSpec.builder(mapperAnnotationClass)
                    .addMember("componentModel", "$S", componentModel)
                    .build();
        }
        return AnnotationSpec.builder(mapperAnnotationClass)
                .build();

    }

    // Get value for the 'value' field of the annotation
    private TypeMirror getClassValue(Mapper annotation) {
        try {
            annotation.value(); // This will throw MirroredTypeException
        } catch (MirroredTypeException e) {
            return e.getTypeMirror();
        }
        return null;
    }

    // Get value for the 'extendsTo' field of the annotation
    private TypeMirror getClassValueForExtends(Mapper annotation) {
        try {
            annotation.extendsTo(); // This will throw MirroredTypeException
        } catch (MirroredTypeException e) {
            return e.getTypeMirror();
        }
        return null;
    }
}
