# FDNS Rules Engine
This is the repository with the Java Library for the Business Rules Engine.

## Usage
Add this into your `pom.xml` to start using the rules engine:

```
<!-- add the dependency to your project -->
<dependency>
  <groupId>gov.cdc</groupId>
  <artifactId>fdns-rules-engine</artifactId>
  <version>1.0.0</version>
</dependency>

<!-- add GitHub CDCgov to repositories -->
<repositories>
  <repository>
    <id>github-cdcgov</id>
    <url>https://github.com/CDCgov/maven-repository/raw/master/</url>
  </repository>
</repositories>
```

### $all

The `$all` operator validates the objects where the value of a field is an array that contains all the specified elements.

**Syntax:**

```
{ "$all": { "<jsonPath1>" : <expression1>, "<jsonPath2>" : <expression2>, ... } } 
```

The `<expression>` can be:
* A JSON array containing the required values 
* A JSON object containing the rule that will be applied on each element returned by `<jsonPath>`

**Example:**

```
{
    "$all": {
        "$.contacts[*]": {
            "hobbies": [
                "Cinema",
                "Ski"
            ]
        }
    }
}
```

### $and

The `$and` operator performs a logical AND operation on an array of two or more `<rules>` and validates objects that satisfy all business rules.

**Syntax:**

```
{ "$and": [ <rule1>, <rule2>, ... { <ruleN> } ] } 
```

### $dateFormat

The `$dateFormat` command validates objects where string values match a date format.

**Syntax:**

```
{ "$dateFormat": { "<jsonPath1>" : <expression1>, "<jsonPath2>" : <expression2>, ... } } 
```

The `<expression>` must be a String value acceptable by Java (reference: https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html).

**Example:**

```
{
    "$dateFormat": {
        "$.contacts": {
            "$.dob": "yyyyMMdd"
        }
    }
}
```

### $eq

The `$eq` operator validates objects where the value of a field equals the specified value.

**Syntax:**

```
{ "$eq": { "<jsonPath1>" : <expression1>, "<jsonPath2>" : <expression2>, ... } } 
```

The `<expression>` can be:
* A basic object such as an integer, a double, a float, a string, a boolean or the `null` value. 
* A JSON object containing the rule that will be applied on each element returned by `<jsonPath>`

**Example:**

```
{
    "$eq": {
        "$.contacts[*]": {
            "age": 33,
            "score": 0.6,
            "enabled": true,
            "ip": null
        }
    }
}
```

### $emptyOrNull

The `$emptyOrNull` command validates objects where the value of the field doesn't exist, is null or is an empty string.

**Syntax:**

```
{ "$emptyOrNull": { "<jsonPath1>" : <expression1>, "<jsonPath2>" : <expression2>, ... } } 
```

The `<expression>` can be:
* A boolean value such as `true` or `false`
* A JSON object containing the rule that will be applied on each element returned by `<jsonPath>`

**Example:**

```
{
    "$emptyOrNull": {
        "$.items[*]": {
            "$.val-1": false,
            "$.val-2": true,
            "$.val-3": true,
            "$.val-4": false
        }
    }
}
```

### $exists

The `$exists` command validates object that contain the field, including object where the field value is null.

**Syntax:**

```
{ "$exists": { "<jsonPath1>" : <expression1>, "<jsonPath2>" : <expression2>, ... } } 
```

The `<expression>` can be:
* A boolean
* A JSON object containing the rule that will be applied on each element returned by `<jsonPath>`

**Example:**

```
{
    "$exists": {
        "$.contacts": true,
        "$.contacts[*]": {
            "name": true,
            "age": false
        }
    }
}
```

### $gt

`$gt` validates those objects where the value of the field is greater than (i.e. `>`) the specified value.

**Syntax:**

```
{ "$gt": { "<jsonPath1>" : <expression1>, "<jsonPath2>" : <expression2>, ... } } 
```

The `<expression>` can be:
* A basic object such as an integer, a double, a float, a string, a boolean. 
* A JSON object containing the rule that will be applied on each element returned by `<jsonPath>`

**Example:**

```
{
    "$gt": {
        "$.contacts[*]": {
            "age": 18,
            "score": 0.5,
            "enabled": false
        }
    }
}
```

### $gte

`$gte` validates those objects where the value of the field is greater than or equal to (i.e. `>=`) the specified value.

**Syntax:**

```
{ "$gte": { "<jsonPath1>" : <expression1>, "<jsonPath2>" : <expression2>, ... } } 
```

The `<expression>` can be:
* A basic object such as an integer, a double, a float, a string, a boolean. 
* A JSON object containing the rule that will be applied on each element returned by `<jsonPath>`

**Example:**

```
{
    "$gte": {
        "$.contacts[*]": {
            "age": 18,
            "score": 0.5,
            "enabled": false
        }
    }
}
```

### $in

The `$in` operator validates the objects where the value of a field equals any value in the specified array.

**Syntax:**

```
{ "$in": { "<jsonPath1>" : <expression1>, "<jsonPath2>" : <expression2>, ... } } 
```

The `<expression>` can be:
* A JSON array containing basic objects such as an integer, a double, a float, a string, a boolean or the `null` value. 
* A JSON object containing the rule that will be applied on each element returned by `<jsonPath>`

**Example:**

```
{
    "$in": {
        "$.contact.name": [
            "Ben",
            "John",
            "Harry"
        ],
        "$.contact.age": [
            30,
            31,
            32,
            33,
            "N/a"
        ],
        "$.contact.score": [
            0,
            0.6
        ],
        "$.contact.enabled": [
            true,
            false
        ],
        "$.contact.ip": [
            null,
            "0.0.0.0"
        ]
    }
}
```

### $lt

`$lt` validates the objects where the value of the field is less than (i.e. `<`) the specified value.

**Syntax:**

```
{ "$lt": { "<jsonPath1>" : <expression1>, "<jsonPath2>" : <expression2>, ... } } 
```

The `<expression>` can be:
* A basic object such as an integer, a double, a float, a string, a boolean. 
* A JSON object containing the rule that will be applied on each element returned by `<jsonPath>`

**Example:**

```
{
    "$lt": {
        "$.contacts[*]": {
            "age": 18,
            "score": 0.5,
            "enabled": false
        }
    }
}
```

### $lte

`$lte` validates the objects where the value of the field is less than or equal (i.e. `<=`) the specified value.

**Syntax:**

```
{ "$lte": { "<jsonPath1>" : <expression1>, "<jsonPath2>" : <expression2>, ... } } 
```

The `<expression>` can be:
* A basic object such as an integer, a double, a float, a string, a boolean. 
* A JSON object containing the rule that will be applied on each element returned by `<jsonPath>`

**Example:**

```
{
    "$lte": {
        "$.contacts[*]": {
            "age": 18,
            "score": 0.5,
            "enabled": true
        }
    }
}
```

### $mod

Validate objects where the value of a field divided by a divisor has the specified remainder (i.e. perform a modulo operation to validate objects).

**Syntax:**

```
{ "$mod": { "<jsonPath1>" : <expression1>, "<jsonPath2>" : <expression2>, ... } } 
```

The `<expression>` can be:
* A JSON array containing two integer values. The first value is the modulo, and the second value is the remainder
* A JSON object containing the rule that will be applied on each element returned by `<jsonPath>`

**Example:**

```
{
    "$mod": {
        "$.contact.age": [3, 0]
    }
}
```

### $ne

`$ne` validates the objects where the value of the field is not equal (i.e. `!=`) to the specified value. This validates objects that do not contain the field.

**Syntax:**

```
{ "$ne": { "<jsonPath1>" : <expression1>, "<jsonPath2>" : <expression2>, ... } } 
```

The `<expression>` can be:
* A basic object such as an integer, a double, a float, a string, a boolean or the `null` value
* A JSON object containing the rule that will be applied on each element returned by `<jsonPath>`

**Example:**

```
{
    "$ne": {
        "$.contact.name": "System",
        "$.contact.age": 18,
        "$.contact.score": 0.5,
        "$.contact.enabled": false,
        "$.contact.ip": null
    }
}
```

### $nin

`$nin` validates the objects where: (a) the field value is not in the specified array or (b) the field does not exist.

**Syntax:**

```
{ "$nin": { "<jsonPath1>" : <expression1>, "<jsonPath2>" : <expression2>, ... } } 
```

The `<expression>` can be:
* A JSON array containing basic objects such as an integer, a double, a float, a string, a boolean or the `null` value
* A JSON object containing the rule that will be applied on each element returned by `<jsonPath>`

**Example:**

```
{
    "$nin": {
        "$.contact.name": [
            "System",
            "Admin",
            "N/a"
        ],
        "$.contact.age": [
            18,
            19,
            20,
            "N/a"
        ],
        "$.contact.score": [
            0,
            0.5
        ],
        "$.contact.enabled": [
            false
        ],
        "$.contact.ip": [
            null,
            "0.0.0.0"
        ]
    }
}
```

### $nor

`$nor` performs a logical NOR operation on an array of one or more business rules and validates objects that fail all the rules in the array.

**Syntax:**

```
{ "$nor": [ <rule1>, <rule2>, ... { <ruleN> } ] } 
```

### $not

`$not` performs a logical NOT operation on the specified `<rule>` and validates the objects that do not match the `<rule>`.

**Syntax:**

```
{ "$not": <rule> } 
```

### $or

The `$or` operator performs a logical OR operation on an array of two or more `<rules>` and validates the object if at least one rule is satisfied.

**Syntax:**

```
{ "$or": [ <rule1>, <rule2>, ... { <ruleN> } ] } 
```

### $regex

Provides regular expression capabilities for pattern matching strings in object validations. If you need help to write your regex pattern, consult this page: https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html.

**Syntax:**

```
{ "$regex": { "<jsonPath1>" : <expression1>, "<jsonPath2>" : <expression2>, ... } } 
```

The `<expression>` can be:
* A string value representing the regex pattern
* A JSON object containing the rule that will be applied on each element returned by `<jsonPath>`

**Example:**

```
{
    "$regex": {
        "$.contacts[*]": {
            "name": "[A-Z][a-z]+"
        }
    }
}
```

### $size

The `$size` operator validates any array with the number of elements specified by the argument.

**Syntax:**

```
{ "$size": { "<jsonPath1>" : <expression1>, "<jsonPath2>" : <expression2>, ... } } 
```

The `<expression>` can be:
* An integer representing the required size
* A JSON object containing the rule that will be applied on each element returned by `<jsonPath>`

**Example:**

```
{
    "$size": {
        "$.contacts[*]": {
            "hobbies": 3
        }
    }
}
```

### $type

The `$type` command validate objects where the value of the field is an instance of the specified type.

**Syntax:**

```
{ "$type": { "<jsonPath1>" : <expression1>, "<jsonPath2>" : <expression2>, ... } }
```

The `<expression>` can be:
* A string value representing the type. This is the allowed list of type to test:
 * Number
 * String
 * Boolean
 * Array
 * Object
 * Null
* A JSON object containing the rule that will be applied on each element returned by `<jsonPath>`

**Example:**

```
{
    "$type": {
        "$.contacts[*]": {
            "name": "String",
            "age": "Number",
            "score": "Number",
            "hobbies": "Array"
        }
    }
}
```

## Custom command

It's possible as well to create your own custom command in Java. If you want to create a command that checks if an array don't contain duplicates. Just create a Java class like this one that implements the abstract class `AbstractCommand` :

```
package gov.cdc.engine.commands;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

import gov.cdc.engine.ValidatorException;
import gov.cdc.engine.result.CompoundValidationResult;
import gov.cdc.engine.result.ValidationResult;

/**
 * The $noDuplicate operator validates any array that contains only unique
 * values.
 * 
 * @author Ben Chevallereau
 *
 */
public class NoDuplicateCommand extends SingleCommand {

  public String getKeyword() {
    return "$noDuplicate";
  }

  @Override
  protected void isValid(JSONObject rule, String jsonPath, Object value, boolean found, CompoundValidationResult result) throws ValidatorException {
    // If the field doesn't exist in the object
    if (!found) {
      result.addResult(ValidationResult.create(false, rule.toString(), getKeyword()));
    } else if (rule.get(jsonPath) instanceof Boolean) {
      Boolean parameter = (Boolean) rule.get(jsonPath);

      boolean valid = false;
      if (value instanceof net.minidev.json.JSONArray) {
        net.minidev.json.JSONArray v = (net.minidev.json.JSONArray) value;
        Set<Object> foundValues = new HashSet<Object>();
        for (int i = 0; i < v.size(); i++)
          foundValues.add(v.get(i));
        valid = foundValues.size() == v.size();
      } else
        throw new ValidatorException("The retrieved value is not a JSON array: " + value);

      if (!parameter)
        valid = !valid;

      result.addResult(ValidationResult.create(valid, rule.toString(), getKeyword()));
    } else if (rule.get(jsonPath) instanceof JSONObject) {
      // If the parameter is a sub-rule
      JSONObject subrule = rule.getJSONObject(jsonPath);
      // Recurse
      result.addAllResults(recurse(subrule, value));
    } else
      throw new ValidatorException("Don't understand the following parameter: " + rule.get(jsonPath));
  }

}
```

Then, you can use your new rule keyword in your JSON configuration:

```
{
    "$noDuplicate": {
        "$.contacts[*]": {
            "hobbies": true
        }
    }
}
```
  
## Public Domain
This repository constitutes a work of the United States Government and is not
subject to domestic copyright protection under 17 USC § 105. This repository is in
the public domain within the United States, and copyright and related rights in
the work worldwide are waived through the [CC0 1.0 Universal public domain dedication](https://creativecommons.org/publicdomain/zero/1.0/).
All contributions to this repository will be released under the CC0 dedication. By
submitting a pull request you are agreeing to comply with this waiver of
copyright interest.

## License
The repository utilizes code licensed under the terms of the Apache Software
License and therefore is licensed under ASL v2 or later.

This source code in this repository is free: you can redistribute it and/or modify it under
the terms of the Apache Software License version 2, or (at your option) any
later version.

This source code in this repository is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
PARTICULAR PURPOSE. See the Apache Software License for more details.

You should have received a copy of the Apache Software License along with this
program. If not, see http://www.apache.org/licenses/LICENSE-2.0.html

The source code forked from other open source projects will inherit its license.


## Privacy
This repository contains only non-sensitive, publicly available data and
information. All material and community participation is covered by the
Surveillance Platform [Disclaimer](https://github.com/CDCgov/template/blob/master/DISCLAIMER.md)
and [Code of Conduct](https://github.com/CDCgov/template/blob/master/code-of-conduct.md).
For more information about CDC's privacy policy, please visit [http://www.cdc.gov/privacy.html](http://www.cdc.gov/privacy.html).

## Contributing
Anyone is encouraged to contribute to the repository by [forking](https://help.github.com/articles/fork-a-repo)
and submitting a pull request. (If you are new to GitHub, you might start with a
[basic tutorial](https://help.github.com/articles/set-up-git).) By contributing
to this project, you grant a world-wide, royalty-free, perpetual, irrevocable,
non-exclusive, transferable license to all users under the terms of the
[Apache Software License v2](http://www.apache.org/licenses/LICENSE-2.0.html) or
later.

All comments, messages, pull requests, and other submissions received through
CDC including this GitHub page are subject to the [Presidential Records Act](http://www.archives.gov/about/laws/presidential-records.html)
and may be archived. Learn more at [http://www.cdc.gov/other/privacy.html](http://www.cdc.gov/other/privacy.html).

## Records
This repository is not a source of government records, but is a copy to increase
collaboration and collaborative potential. All government records will be
published through the [CDC web site](http://www.cdc.gov).

## Notices
Please refer to [CDC's Template Repository](https://github.com/CDCgov/template)
for more information about [contributing to this repository](https://github.com/CDCgov/template/blob/master/CONTRIBUTING.md),
[public domain notices and disclaimers](https://github.com/CDCgov/template/blob/master/DISCLAIMER.md),
and [code of conduct](https://github.com/CDCgov/template/blob/master/code-of-conduct.md).
