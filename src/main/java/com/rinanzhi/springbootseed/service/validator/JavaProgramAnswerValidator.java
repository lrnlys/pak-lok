package com.rinanzhi.springbootseed.service.validator;

import com.rinanzhi.springbootseed.enums.QuestionType;
import com.rinanzhi.springbootseed.exception.AnswerException;
import com.rinanzhi.springbootseed.exception.CompileAnswerException;
import com.rinanzhi.springbootseed.util.StringFileObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import static com.rinanzhi.springbootseed.service.validator.AnswerClassLoader.SEPARATOR;
import static com.rinanzhi.springbootseed.util.UuidUtils.randomUuid;
import static java.lang.String.join;

@Log4j2
public class JavaProgramAnswerValidator extends AnswerValidator {

    @Autowired
    private AnswerClassLoader loader;

    @Override
    protected String getAnswer(Long questionId, String answer) {
        try {
            String executeToken = randomUuid();
            String className = this.compileAnswer(loader, executeToken, questionId.toString(), answer);
            Class answerClazz = loader.findClass(className);
            Object answerInstance = answerClazz.newInstance();
            Method answerMethod = BeanUtils.findMethod(answerClazz, "answer");
            return (String) answerMethod.invoke(answerInstance);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            log.error(e);
            return null;
        } catch (CompileAnswerException e) {
            throw new AnswerException(e.getMessage());
        }
    }

    protected String compileAnswer(AnswerClassLoader loader, String executeToken, String name, String answer) throws CompileAnswerException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        AtomicReference<String> message = new AtomicReference<>();

        StandardJavaFileManager fileManager = compiler.getStandardFileManager(
                diagnostic -> message.set(diagnostic.getMessage(Locale.getDefault())),
                Locale.getDefault(), Charset.forName("UTF-8"));

        JavaFileObject srcObject = new StringFileObject(name, answer);
        Iterable<JavaFileObject> fileObjects = Arrays.asList(srcObject);

        String flag = "-d";
        File classPath = loader.getExclusivePath(executeToken).toFile();
        String className = join(SEPARATOR, executeToken, name);
        Iterable<String> options = Arrays.asList(flag, classPath.getAbsolutePath());
        Writer out = new CharArrayWriter();

        JavaCompiler.CompilationTask task = compiler.getTask(out, fileManager,
                diagnostic -> message.set(diagnostic.getMessage(Locale.getDefault())),
                options, null, fileObjects);

        boolean result = task.call();
        if (!result) {
            throw new CompileAnswerException(message.get());
        }
        log.info("Compile it successfully.");
        return className;
    }

}
