package groupEntity;

import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationOption;

import java.io.Serializable;
import java.util.List;

/**
 * @Progrem: pinyougou_parent
 * @Author: weihaibin
 * @Date: 2018-12-06 16:22
 */
public class Specification implements Serializable{

    private TbSpecification Specification;

    private List<TbSpecificationOption> specificationOptions;

    public TbSpecification getSpecification() {
        return Specification;
    }

    public void setSpecification(TbSpecification specification) {
        Specification = specification;
    }

    public List<TbSpecificationOption> getSpecificationOptions() {
        return specificationOptions;
    }

    public void setSpecificationOptions(List<TbSpecificationOption> specificationOptions) {
        this.specificationOptions = specificationOptions;
    }
}
